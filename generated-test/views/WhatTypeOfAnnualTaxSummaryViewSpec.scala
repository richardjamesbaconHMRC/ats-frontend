package views

import play.api.data.Form
import controllers.routes
import forms.WhatTypeOfAnnualTaxSummaryFormProvider
import views.behaviours.YesNoViewBehaviours
import models.NormalMode
import views.html.whatTypeOfAnnualTaxSummary

class WhatTypeOfAnnualTaxSummaryViewSpec extends YesNoViewBehaviours {

  val messageKeyPrefix = "whatTypeOfAnnualTaxSummary"

  val form = new WhatTypeOfAnnualTaxSummaryFormProvider()()

  def createView = () => whatTypeOfAnnualTaxSummary(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  def createViewUsingForm = (form: Form[_]) => whatTypeOfAnnualTaxSummary(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  "WhatTypeOfAnnualTaxSummary view" must {

    behave like normalPage(createView, messageKeyPrefix)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix, routes.WhatTypeOfAnnualTaxSummaryController.onSubmit(NormalMode).url)
  }
}
