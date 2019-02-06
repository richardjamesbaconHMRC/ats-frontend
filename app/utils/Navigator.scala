/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils

import javax.inject.{Inject, Singleton}
import play.api.mvc.Call
import controllers.routes
import models.WhatTypeOfAnnualTaxSummaryQuestion
import pages._
import views.html.whatTypeOfAnnualTaxSummary

@Singleton
class Navigator @Inject()() {

  private def whatTypeOfAnnualTaxSummaryPageRouting(userAnswers: UserAnswers) = {
    val answer = userAnswers.cacheMap.data("whatTypeOfAnnualTaxSummary").toString().replace("\"", "")

    answer match {
      case WhatTypeOfAnnualTaxSummaryQuestion.Percentages.toString => routes.HowYourTaxWasSpentController.onPageLoad()
      case WhatTypeOfAnnualTaxSummaryQuestion.Amounts.toString  => routes.IndexController.onPageLoad()
    }
  }

  private val routeMap: Map[Page, UserAnswers => Call] = {
    Map(
      WhatTypeOfAnnualTaxSummaryPage       -> whatTypeOfAnnualTaxSummaryPageRouting
      //    EasinessPage       -> (_ => routes.ReasonForScoreController.onPageLoad()),
      //    ReasonForScorePage -> (_ => routes.SatisfactionController.onPageLoad()),
      //    SatisfactionPage   -> (_ => routes.ThankYouController.onPageLoad())
    )
  }

  lazy val firstPage: Call = routes.WhatTypeOfAnnualTaxSummaryController.onPageLoad()

  def nextPage(page: Page): UserAnswers => Call =
      routeMap.getOrElse(page, _ => routes.IndexController.onPageLoad())
}
