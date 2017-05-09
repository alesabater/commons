package deloitte.ie.bigdata.commons

import deloitte.ie.bigdata.commons.LoadedProperties.conf
import org.scalatest.{FlatSpec, Matchers}

class LoadedPropertiesTest extends FlatSpec with Matchers {

  "Properties" should "load" in {

    conf.getString("application.id") shouldEqual "NOT-SET"
  }

  it should "overload with env configs" in {

    conf.getString("test.envProp") shouldEqual "env"
  }


}