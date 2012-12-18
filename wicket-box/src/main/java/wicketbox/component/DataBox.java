/*
 * Copyright 2012 Sven Meier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicketbox.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import wicketbox.AbstractBoxBehavior;
import wicketbox.Orientation;
import wicketbox.Resize;
import wicketbox.Scroll;
import wicketbox.Stretch;

/**
 * A {@link DataTable} with the following enhancements:
 * <ul>
 * <li>the body is {@link Stretch}ed so that the footer is attached to the
 * bottom</li>
 * <li>columns can be {@link Resize}ed, the widths are persisted in a cookie</li>
 * <li> {@link Scroll}ing between header and body is synchronized, the position
 * is persisted in the document</li>
 * </ul>
 * Note: Unlike {@link DataTable} this subclass must not be bound to a
 * {@code <table>} markup tag.
 * 
 * @see AbstractBoxBehavior#persistInCookie(String, int)
 * @see AbstractBoxBehavior#persistInDocument(String)
 * 
 * @author Sven Meier
 */
public class DataBox<T, S> extends DataTable<T, S> {

	private static final int MAX_AGE = 30 * 24 * 60 * 60;

	private static final int DEFAULT_WIDTH = 64;

	public DataBox(String id, List<? extends IColumn<T, S>> columns,
			IDataProvider<T> dataProvider, long rowsPerPage) {
		super(id, columns, dataProvider, rowsPerPage);

		add(new Stretch(Orientation.VERTICAL, ".box-table-top",
				".box-table-body", ".box-table-bottom"));

		add(new Resize(".box-table-top table", ".box-table-body table",
				new WidthsModel()) {
			protected String getPersist(Component component) {
				return persistInCookie(
						"resize:" + component.getPageRelativePath(), MAX_AGE);
			}
		});

		add(new Scroll(Orientation.HORIZONTAL,
				".box-table-top, .box-table-body") {
			protected String getPersist(Component component) {
				return persistInDocument("scroll:" + component.getMarkupId());
			}
		});
	}

	/**
	 * Get the initial width for the given column.
	 * 
	 * @param column
	 *            the column
	 * @return initial width
	 */
	protected int getWidth(IColumn<?, ?> column) {
		return DEFAULT_WIDTH;
	}

	private class WidthsModel implements IModel<List<Integer>> {

		public void detach() {
		}

		public List<Integer> getObject() {
			List<Integer> widths = new ArrayList<Integer>();

			for (IColumn<?, ?> column : getColumns()) {
				widths.add(DataBox.this.getWidth(column));
			}

			return widths;
		}

		public void setObject(List<Integer> object) {
		}
	}
}