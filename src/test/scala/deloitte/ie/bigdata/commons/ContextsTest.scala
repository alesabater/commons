package deloitte.ie.bigdata.commons

import org.apache.spark.sql.DataFrame
import org.scalatest.{FlatSpec, Matchers}
import deloitte.ie.bigdata.commons.Environment.Env._

class ContextsTest extends FlatSpec with Matchers {

  "contexts" should "be configured" in {
    Contexts.sqlCtx.emptyDataFrame
  }

  it should "create DataFrame" in {
    val df = Contexts.sqlCtx.createDataFrame(Seq(
      (1,2),
      (3,4),
      (5,6)
    )).toDF("int","int1")
    df shouldBe a [DataFrame]
    df.columns shouldEqual Array("int","int1")
    df.count() shouldEqual 3
    df.show()
  }

  it should "have master set to local" in {
    Environment.current match {
      case PROD => Contexts.sc.getConf.get("spark.master") shouldEqual "yarn-cluster"
      case TEST => Contexts.sc.getConf.get("spark.master") shouldEqual "yarn-cluster"
      case DEV => Contexts.sc.getConf.get("spark.master") shouldEqual "local"
    }
  }

}