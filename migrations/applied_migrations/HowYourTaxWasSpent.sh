#!/bin/bash

echo "Applying migration HowYourTaxWasSpent"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /howYourTaxWasSpent                       controllers.HowYourTaxWasSpentController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "howYourTaxWasSpent.title = howYourTaxWasSpent" >> ../conf/messages.en
echo "howYourTaxWasSpent.heading = howYourTaxWasSpent" >> ../conf/messages.en

echo "Migration HowYourTaxWasSpent completed"
