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

package models

import play.api.libs.json._
import viewmodels.RadioOption

sealed trait WhatTypeOfAnnualTaxSummaryQuestion

object WhatTypeOfAnnualTaxSummaryQuestion {

  case object Percentages extends WithName("Percentages") with WhatTypeOfAnnualTaxSummaryQuestion
  case object Amounts     extends WithName("Amounts") with WhatTypeOfAnnualTaxSummaryQuestion

  val values: Seq[WhatTypeOfAnnualTaxSummaryQuestion] =
    List(Percentages, Amounts)

  val options: Seq[RadioOption] = values.map {
    value =>
      RadioOption("whatTypeOfAnnualTaxSummaryQuestion", value.toString)
  }

  implicit val enumerable: Enumerable[WhatTypeOfAnnualTaxSummaryQuestion] =
    Enumerable(values.map(v => v.toString -> v): _*)

  implicit object WhatTypeOfAnnualTaxSummaryQuestionWrites extends Writes[WhatTypeOfAnnualTaxSummaryQuestion] {
    def writes(whatTypeOfAnnualTaxSummaryQuestion: WhatTypeOfAnnualTaxSummaryQuestion) =
      Json.toJson(whatTypeOfAnnualTaxSummaryQuestion.toString)
  }

  implicit object MainServiceQuestionReads extends Reads[WhatTypeOfAnnualTaxSummaryQuestion] {
    override def reads(json: JsValue): JsResult[WhatTypeOfAnnualTaxSummaryQuestion] = json match {
      case JsString(Percentages.toString) => JsSuccess(Percentages)
      case JsString(Amounts.toString)     => JsSuccess(Amounts)
      case _                              => JsError("Unknown WhatTypeOfAnnualTaxSummaryQuestion")
    }
  }
}


