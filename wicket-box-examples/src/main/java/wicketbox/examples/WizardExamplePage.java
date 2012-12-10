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

import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.ResourceModel;

import wicketbox.component.WizardBox;
import wicketbox.component.theme.BasicTheme;

/**
 * @author Sven Meier
 */
public class WizardExamplePage extends ExamplePage {

	public WizardExamplePage() {
		WizardBox wizard = new WizardBox("wizard", newWizardModel());
		wizard.add(new BasicTheme());
		add(wizard);
	}

	private IWizardModel newWizardModel() {
		WizardModel wizard = new WizardModel();

		wizard.add(new ExampleWizardStep(1));
		wizard.add(new ExampleWizardStep(2));
		wizard.add(new ExampleWizardStep(3));
		wizard.add(new ExampleWizardStep(4));

		return wizard;
	}

	private class ExampleWizardStep extends WizardStep {

		public ExampleWizardStep(int index) {
			super(new ResourceModel(index + ".title"), new ResourceModel(index
					+ ".summary"));

			add(new MultiLineLabel("label", new TimesModel(index)));
		}
	}

	private class TimesModel extends AbstractReadOnlyModel<String> {

		private int i;
		private ResourceModel model;

		public TimesModel(int i) {
			this.i = i;
			this.model = new ResourceModel("lorem");
		}

		@Override
		public void detach() {
			super.detach();

			this.model.detach();
		}

		@Override
		public String getObject() {
			StringBuilder string = new StringBuilder();

			for (int i = 0; i < this.i; i++) {
				string.append(model.getObject());
			}

			return string.toString();
		}
	}
}