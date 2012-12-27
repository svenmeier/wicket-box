;(function (undefined) {

	"use strict";
	
	if (typeof(window.wicketbox) === 'undefined') {
		window.wicketbox = {

			MIN: 16,
			
			MARGIN: 4,
			
			stretch: function(id, orientation, selectors) {
				var element = document.getElementById(id);

				apply();
				
				$(element).on('resize', function(event) {
					apply();
				});
				
				function apply() {
					var method = 'HORIZONTAL' == orientation ? 'outerWidth' : 'outerHeight';

					var leading = 0;
					$(element).find(selectors.leading).each(function(index, child) {
						leading += $(child)[method]();
					});
								
					var trailing = 0;
					$(element).find(selectors.trailing).each(function(index, child) {
						trailing += $(child)[method]();
					});

					if (orientation == 'HORIZONTAL') {
						$(element).find(selectors.center).css({'left': leading + 'px', 'right': trailing + 'px'});
					} else {
						$(element).find(selectors.center).css({'top': leading + 'px', 'bottom': trailing + 'px'});
					}
				};
			},
			
			resize: function(id, orientation, selector, persist, size) {
				var element = document.getElementById(id);

				loadSize();

				apply();
				
				$(element).find(selector).on('mousedown', function(event) {
					
					var initialSize = size;
					
					wicketbox.drag(orientation, event, function(deltaX, deltaY) {
						if (orientation == 'HORIZONTAL') {
							size = initialSize + deltaX;
						} else {
							size = initialSize + deltaY;
						}
						if (size < wicketbox.MIN) {
							size = wicketbox.MIN;
						}
						
						apply();
						
						$(element).find(selector).trigger('resize');
					}, function() {
						saveSize();
					});
				});

				function apply() {
					if (orientation == 'HORIZONTAL') {
						$(element).css({'width': size + 'px'});
					} else {
						$(element).css({'height': size + 'px'});
					}
				};
				
				function loadSize() {
					size = parseInt(persist()) || size;
				};
				
				function saveSize() {
					persist(size);
				};				
			},
			
			scroll: function(id, orientation, selector, persist, position) {
				var element = document.getElementById(id);
				var synchronizing;
				
				var method = 'HORIZONTAL' == orientation ? 'scrollLeft' : 'scrollTop';
				
				loadScroll();
				
				// scroll events do not bubble :(
				$(element).find(selector).on('scroll', function(event) {
					position = $(event.target).closest(selector)[method]();
						
					$(element).children(selector).each(function(index, child) {
						if ($(child)[method]() != position) {
							$(child)[method](position);
						}
					});
					
					saveScroll();
				});
				
				function loadScroll() {
					position = parseInt(persist()) || position;
					
					$(element).find(selector)[method](position);
				};

				function saveScroll() {
					persist(position);
				};				
			},

			colresize: function(id, selectors, persist, widths) {
				var element = document.getElementById(id);
				var resizing;

				loadWidths();

				$(element).on('mousemove', selectors.header, function(event) {				
					if (!resizing) {
						var column = findColumn(event);

						if (column != undefined) {
							$(event.target).closest(selectors.header).addClass('box-resizing-HORIZONTAL');
						} else {
							$(event.target).closest(selectors.header).removeClass('box-resizing-HORIZONTAL');
						}
					}
				});
				
				$(element).on('mousedown', selectors.header + ' tr', function(event) {
					var column = findColumn(event);

					if (column != undefined) {
						resizing = true;

						var cols = $(element).find('col:nth-child(' + (column + 1) + ')');
						var initialWidth = parseInt(cols.attr('width')) || widths[column];

						wicketbox.drag(event, function(deltaX, deltaY) {
							var newWidth = initialWidth + deltaX;
							if (newWidth < wicketbox.MIN) {
								newWidth = wicketbox.MIN;
							}
							cols.attr('width', newWidth);
							
							// Opera does not trigger scroll events, even if resizing forces a scroll :(
							$(element).find(selectors.body).trigger('scroll');
						}, function() {
							resizing = false;

							saveWidths();
						});
					}
				});

				function findColumn(event) {
					var th = $(event.target).closest('th');
					if (th.size()) {
						var offset = th.offset();
						var width = th.outerWidth();
						
						if (event.pageX > offset.left + width - wicketbox.MARGIN) {
							return th.index();
						}
						
						if (event.pageX < offset.left + wicketbox.MARGIN) {
							if (th.index() > 0) {
								return th.index() -1;
							}
						}
					}					
					return undefined;
				};

				function loadWidths() {
					var oldWidths = persist() || '';
					if (oldWidths) {
						oldWidths = oldWidths.split(':');
					}
					
					$(element).find(selectors.header + ', ' + selectors.body).each(function(index, table) {
						var colgroup = $('<colgroup>').prependTo(table);
						
						$.each(widths, function(index, width) {
							var col = $('<col>').appendTo(colgroup);
							
							if (oldWidths.length > index && oldWidths[index] != '') {
								width = oldWidths[index] || width;
							}
							col.attr('width', width);
						});

						// Firefox wants an additional column to take additional space 
						colgroup.append('<col>');
					});
				};

				function saveWidths() {
					var widths = $(element).find(selectors.header + ' col').map(function(index, col) {
						return $(col).attr('width');
					}).get().join(':');
					
					persist(widths);
				};
			},

			drag: function(event, onDragging, onDragged) {
				event.preventDefault();
				event.stopPropagation();
				
				var initialX = event.pageX;
				var initialY = event.pageY;
				
				$(document).on('mousemove.wicketbox', function(event) {
					event.preventDefault();
					event.stopPropagation();
					
					if (onDragging) {
						onDragging(event.pageX - initialX, event.pageY - initialY);
					}
				});
				
				$(document).on('mouseup.wicketbox', function(event) {
					event.preventDefault();
					event.stopPropagation();

					$(document).off('.wicketbox');

					if (onDragged) {
						onDragged();
					}
				});					
			},
			
			persistNot: function() {
				return function(value) {
				};
			},
						
			persistInCookie: function(key, maxAge) {
				return function(value) {
					if (value) {
						var suffix ='; path=/';
						if (maxAge) {
							suffix += '; expires=' + new Date(new Date().getTime() + (maxAge * 1000)).toGMTString();
						}
		
						document.cookie = key + '=' + value + suffix;
					} else {
						$.each(document.cookie.split(';'), function(index, entry) {
							var keyValue = entry.split('=');
							if ($.trim(keyValue[0]) == key) {
								value = $.trim(keyValue[1]);
								return false;
							}
						});
						
						return value;
					}
				};
			},	

			persistInDocument: function(key) {
				return function(value) {
					if (value === undefined) {
						return $(document).data(key);
					} else {
						$(document).data(key, value);
					}
				};
			},
			
			persistOnServer: function(callbackUrl, channel) {
				return function(value) {
					if (value) {
						var attrs = {
							u: callbackUrl,
							ep: {},
							ch: channel
						};
						attrs.ep['value'] = value;
						Wicket.Ajax.ajax(attrs);
					}
				};
			}	
		};
	}
})();
