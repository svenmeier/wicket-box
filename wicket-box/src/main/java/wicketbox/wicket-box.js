;(function (undefined) {

	"use strict";
	
	if (typeof(window.wicketbox) === 'undefined') {
		window.wicketbox = {

			MIN: 16,
			
			MARGIN: 4,

			stretch: function(id, selectors) {
				var element = document.getElementById(id);

				var top = 0;
				$(element).children(selectors.top).each(function(index, child) {
					top += $(child).outerHeight();
				});
							
				var bottom = 0;
				$(element).children(selectors.bottom).each(function(index, child) {
					bottom += $(child).outerHeight();
				});

				$(element).children(selectors.center).css({'top': top + 'px', 'bottom': bottom + 'px'});
			},
			
			synchronizedScroll: function(id, orientation, selector, persist, value) {
				var element = document.getElementById(id);
				var synchronizing;
				
				loadScroll();
				
				$(element).find(selector).on('scroll', function(event) {
					value = $(event.target)[orientation]();
						
					$(element).children(selector).each(function(index, child) {
						if ($(child)[orientation]() != value) {
							$(child)[orientation](value);
						}
					});
					
					saveScroll(value);
				});
				
				function loadScroll() {
					value = parseInt(persist()) || value;
					
					$(element).find(selector)[orientation](value);
				};

				function saveScroll(value) {
					persist(value);
				};				
			},

			resizableColumns: function(id, selectors, persist, widths) {
				var element = document.getElementById(id);
				var resizing;

				loadWidths();

				$(element).on('mousemove.wicketbox', selectors.header, function(event) {				
					if (!resizing) {
						var column = findColumn(event);

						if (column != undefined) {
							$(event.target).closest(selectors.header).addClass('resizing');
						} else {
							$(event.target).closest(selectors.header).removeClass('resizing');
						}
					}
				});
				
				$(element).on('mousedown.wicketbox', selectors.header + ' tr', function(event) {
					var column = findColumn(event);

					if (column != undefined) {
						event.preventDefault();
						event.stopPropagation();
						
						resize(column, event.pageX);
					}
				});

				function resize(column, initialX) {
					resizing = true;

					var cols = $(element).find('col:nth-child(' + (column + 1) + ')');
					var initialWidth = parseInt(cols.attr('width')) || widths[column];

					$(document).on('mousemove.wicketbox', function(event) {
						event.preventDefault();
						event.stopPropagation();
						
						var newWidth = initialWidth + (event.pageX - initialX);
						if (newWidth < wicketbox.MIN) {
							newWidth = wicketbox.MIN;
						}
						cols.attr('width', newWidth);
					});
					
					$(document).on('mouseup.wicketbox', function(event) {
						event.preventDefault();
						event.stopPropagation();
						
						$(document).off('.wicketbox');

						resizing = false;

						saveWidths();
					});
				};
				
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
