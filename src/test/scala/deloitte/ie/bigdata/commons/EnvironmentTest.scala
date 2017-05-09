package deloitte.ie.bigdata.commons

import com.decodified.scalassh.{HostConfig, PublicKeyLogin, SSH, _}
import org.mockito.Mockito
import org.scalatest.{FlatSpec, Matchers}
import deloitte.ie.bigdata.commons.Environment.Env

class EnvironmentTest extends FlatSpec with Matchers {

  "environment" should "parse a TEST env" in {
    val hstring = "lhansahdp1"
    Environment.parseEnv(hstring) should equal(Env.TEST)
    Env.TEST.toString should equal("test")
  }

  it should "parse a PROD env" in {
    val hstring = "LHANSAHDP2"
    Environment.parseEnv(hstring) should equal(Env.PROD)
    Env.PROD.toString should equal("prod")
  }

  it should "parse a DEV env" in {
    val hstring = "anything really"
    Environment.parseEnv(hstring) should equal(Env.DEV)
    Env.DEV.toString should equal("dev")
  }

  "current" should "return DEV when HDFS client is not available" in {
    Environment.current should equal(Env.DEV)
  }

  "getServiceName" should "get the cluster" in {
    val conf = Mockito.mock(classOf[shell])
    val expectedResult =
      """Hostname: lhansahdp1-230-18
        | """.stripMargin
    Mockito.when(conf.hdfsReportCapture) thenReturn expectedResult
    val result = Environment.getServiceName(conf)
    result shouldEqual "lhansahdp1"
  }
}
