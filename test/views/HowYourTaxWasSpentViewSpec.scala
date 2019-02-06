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

package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest._
import play.api.i18n.Messages
import play.twirl.api.Html
import views.behaviours.ViewBehaviours
import views.html._

class HowYourTaxWasSpentViewSpec extends ViewBehaviours with MustMatchers {

  implicit override val messages: Messages = play.api.i18n.Messages.Implicits.applicationMessages

  def createView: Html = howYourTaxWasSpent(frontendAppConfig, frontendAppConfig.percentageData.keySet.toSeq)(fakeRequest, messages)
  def doc: Document = Jsoup.parse(createView.toString())
  val percentChar: String = "%"

  "HowYourTaxWasSpent view" must {

    "Display the correct title" in {
      doc.title mustBe messages("howYourTaxWasSpent.title")
    }

    "Display the correct heading" in {
      doc.getElementById("heading").text mustBe messages("howYourTaxWasSpent.heading")
    }

    "Display the correct subheading" in {
      doc.getElementById("subheading").text mustBe messages("howYourTaxWasSpent.subheading")
    }

    "Display the correct caveat" in {
      doc.getElementById("caveat").text mustBe messages("howYourTaxWasSpent.caveat")
    }

    "Rendered table" should {

      val keys: Seq[String] = frontendAppConfig.percentageData.keySet.toSeq

      for (key <- keys) {

        s"Render $key values properly" in {

          doc.body.getElementById(key).child(0).text() mustBe messages(s"howYourTaxWasSpent.table.department.$key")
          doc.body.getElementById(key).child(1).text() mustBe frontendAppConfig.percentageData(key) + percentChar
        }
      }
    }
  }
}
