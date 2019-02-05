package forms

import forms.behaviours.BooleanFieldBehaviours
import play.api.data.FormError

class WhatTypeOfAnnualTaxSummaryFormProviderSpec extends BooleanFieldBehaviours {

  val requiredKey = "whatTypeOfAnnualTaxSummary.error.required"
  val invalidKey = "error.boolean"

  val form = new WhatTypeOfAnnualTaxSummaryFormProvider()()

  ".value" must {

    val fieldName = "value"

    behave like booleanField(
      form,
      fieldName,
      invalidError = FormError(fieldName, invalidKey)
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )
  }
}
