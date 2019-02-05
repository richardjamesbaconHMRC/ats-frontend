package pages

import pages.behaviours.PageBehaviours

class WhatTypeOfAnnualTaxSummaryPageSpec extends PageBehaviours {

  "WhatTypeOfAnnualTaxSummaryPage" must {

    beRetrievable[Boolean](WhatTypeOfAnnualTaxSummaryPage)

    beSettable[Boolean](WhatTypeOfAnnualTaxSummaryPage)

    beRemovable[Boolean](WhatTypeOfAnnualTaxSummaryPage)
  }
}
