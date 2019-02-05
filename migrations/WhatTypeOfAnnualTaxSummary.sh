#!/bin/bash

echo "Applying migration WhatTypeOfAnnualTaxSummary"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/app.routes
echo "GET        /whatTypeOfAnnualTaxSummary                        controllers.WhatTypeOfAnnualTaxSummaryController.onPageLoad(mode: Mode = NormalMode)" >> ../conf/app.routes
echo "POST       /whatTypeOfAnnualTaxSummary                        controllers.WhatTypeOfAnnualTaxSummaryController.onSubmit(mode: Mode = NormalMode)" >> ../conf/app.routes

echo "GET        /changeWhatTypeOfAnnualTaxSummary                  controllers.WhatTypeOfAnnualTaxSummaryController.onPageLoad(mode: Mode = CheckMode)" >> ../conf/app.routes
echo "POST       /changeWhatTypeOfAnnualTaxSummary                  controllers.WhatTypeOfAnnualTaxSummaryController.onSubmit(mode: Mode = CheckMode)" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "whatTypeOfAnnualTaxSummary.title = whatTypeOfAnnualTaxSummary" >> ../conf/messages.en
echo "whatTypeOfAnnualTaxSummary.heading = whatTypeOfAnnualTaxSummary" >> ../conf/messages.en
echo "whatTypeOfAnnualTaxSummary.checkYourAnswersLabel = whatTypeOfAnnualTaxSummary" >> ../conf/messages.en
echo "whatTypeOfAnnualTaxSummary.error.required = Select yes if whatTypeOfAnnualTaxSummary" >> ../conf/messages.en

echo "Adding to UserAnswersEntryGenerators"
awk '/trait UserAnswersEntryGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryWhatTypeOfAnnualTaxSummaryUserAnswersEntry: Arbitrary[(WhatTypeOfAnnualTaxSummaryPage.type, JsValue)] =";\
    print "    Arbitrary {";\
    print "      for {";\
    print "        page  <- arbitrary[WhatTypeOfAnnualTaxSummaryPage.type]";\
    print "        value <- arbitrary[Boolean].map(Json.toJson(_))";\
    print "      } yield (page, value)";\
    print "    }";\
    next }1' ../test/generators/UserAnswersEntryGenerators.scala > tmp && mv tmp ../test/generators/UserAnswersEntryGenerators.scala

echo "Adding to PageGenerators"
awk '/trait PageGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryWhatTypeOfAnnualTaxSummaryPage: Arbitrary[WhatTypeOfAnnualTaxSummaryPage.type] =";\
    print "    Arbitrary(WhatTypeOfAnnualTaxSummaryPage)";\
    next }1' ../test/generators/PageGenerators.scala > tmp && mv tmp ../test/generators/PageGenerators.scala

echo "Adding to CacheMapGenerator"
awk '/val generators/ {\
    print;\
    print "    arbitrary[(WhatTypeOfAnnualTaxSummaryPage.type, JsValue)] ::";\
    next }1' ../test/generators/CacheMapGenerator.scala > tmp && mv tmp ../test/generators/CacheMapGenerator.scala

echo "Adding helper method to CheckYourAnswersHelper"
awk '/class/ {\
     print;\
     print "";\
     print "  def whatTypeOfAnnualTaxSummary: Option[AnswerRow] = userAnswers.get(WhatTypeOfAnnualTaxSummaryPage) map {";\
     print "    x => AnswerRow(\"whatTypeOfAnnualTaxSummary.checkYourAnswersLabel\", if(x) \"site.yes\" else \"site.no\", true, routes.WhatTypeOfAnnualTaxSummaryController.onPageLoad(CheckMode).url)"; print "  }";\
     next }1' ../app/utils/CheckYourAnswersHelper.scala > tmp && mv tmp ../app/utils/CheckYourAnswersHelper.scala

echo "Migration WhatTypeOfAnnualTaxSummary completed"
