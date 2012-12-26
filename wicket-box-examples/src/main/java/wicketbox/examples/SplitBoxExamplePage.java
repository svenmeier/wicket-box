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
package wicketbox.examples;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import wicketbox.component.SplitBox;
import wicketbox.component.theme.BasicTheme;

/**
 * @author Sven Meier
 */
public class SplitBoxExamplePage extends ExamplePage {

	public SplitBoxExamplePage() {

		Label main = new Label("main", new StringResourceModel("lorem", null));
		Label remainder = new Label("remainder", new StringResourceModel(
				"lorem", null));

		SplitBox split = new SplitBox("split", main, remainder, Model.of(64));
		split.add(new BasicTheme());
		add(split);
	}
}