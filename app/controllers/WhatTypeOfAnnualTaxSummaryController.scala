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

package controllers

import javax.inject.Inject
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import connectors.DataCacheConnector
import controllers.actions._
import config.FrontendAppConfig
import forms.WhatTypeOfAnnualTaxSummaryFormProvider
import models.{Mode, WhatTypeOfAnnualTaxSummaryQuestion}
import pages.WhatTypeOfAnnualTaxSummaryPage
import utils.Navigator
import utils.UserAnswers
import views.html.whatTypeOfAnnualTaxSummary

import scala.concurrent.Future

class WhatTypeOfAnnualTaxSummaryController @Inject()(appConfig: FrontendAppConfig,
                                         override val messagesApi: MessagesApi,
                                         dataCacheConnector: DataCacheConnector,
                                         navigator: Navigator,
                                         identify: IdentifierAction,
                                         getData: DataRetrievalAction,
                                         formProvider: WhatTypeOfAnnualTaxSummaryFormProvider
                                         ) extends FrontendController with I18nSupport {

  val form: Form[WhatTypeOfAnnualTaxSummaryQuestion] = formProvider()

  def onPageLoad() = (identify andThen getData) {
    implicit request =>

      val preparedForm = request.userAnswers.getOrElse(UserAnswers(request.internalId)).get(WhatTypeOfAnnualTaxSummaryPage) match {
        case None => form
        case Some(value) => form.fill(value)
      }

      Ok(whatTypeOfAnnualTaxSummary(appConfig, preparedForm))
  }

  def onSubmit() = (identify andThen getData).async {
    implicit request =>

      form.bindFromRequest().fold(
        (formWithErrors: Form[_]) =>
          Future.successful(BadRequest(whatTypeOfAnnualTaxSummary(appConfig, formWithErrors))),
        (value) => {
          val updatedAnswers = request.userAnswers.getOrElse(UserAnswers(request.internalId)).set(WhatTypeOfAnnualTaxSummaryPage, value)

//          println("-")
//          updatedAnswers.cacheMap.data map { answer =>
//            println(answer._2)
//          }
//          println("-")

          dataCacheConnector.save(updatedAnswers.cacheMap).map(
            _ =>
              Redirect(navigator.nextPage(WhatTypeOfAnnualTaxSummaryPage)(updatedAnswers))
          )
        }
      )
  }
}
