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

import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Sven Meier
 */
public class WicketApplication extends WebApplication {
	public WicketApplication() {
	}

	@Override
	protected void init() {
		getMarkupSettings().setStripWicketTags(true);

		mountPage("tree", TreeBoxExamplePage.class);
		mountPage("wizard", WizardBoxExamplePage.class);
		mountPage("split", SplitBoxExamplePage.class);
	}

	public Class<DataBoxExamplePage> getHomePage() {
		return DataBoxExamplePage.class;
	}
}
