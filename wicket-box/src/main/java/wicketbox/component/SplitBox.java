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

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import wicketbox.Orientation;
import wicketbox.Resize;
import wicketbox.Stretch;

/**
 * @author Sven Meier
 */
public class SplitBox extends Panel {

	private static final int MAX_AGE = 30 * 24 * 60 * 60;

	public SplitBox(String id, Component main, Component remainder,
			IModel<Integer> size) {
		super(id);

		add(new Stretch(Orientation.HORIZONTAL, ".box-split-wrapper",
				".box-split-remainder", null));

		WebMarkupContainer wrapper = new WebMarkupContainer("wrapper");
		wrapper.add(new Resize(Orientation.HORIZONTAL, ".box-split-divider") {
			@Override
			protected String getPersist(Component component) {
				return persistInCookie(
						"resize:" + component.getPageRelativePath(), MAX_AGE);
			}
		});
		add(wrapper);

		Args.isTrue("main".equals(main.getId()), "main");
		wrapper.add(main);

		wrapper.add(newDivider("divider"));

		Args.isTrue("remainder".equals(remainder.getId()), "remainder");
		add(remainder);
	}

	/**
	 * Create a component representing the divider.
	 * 
	 * @param id
	 *            component id
	 * @return
	 */
	protected Component newDivider(String id) {
		return new WebMarkupContainer(id);
	}
}