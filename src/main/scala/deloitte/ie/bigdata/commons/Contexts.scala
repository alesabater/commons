package deloitte.ie.bigdata.commons

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import deloitte.ie.bigdata.commons.LoadedProperties._

object Contexts {

  private val appId = conf.getString("application.id")
  //private val appId = "just-in-case"
  private val master = Environment.master

  private lazy val sparkConf = new SparkConf()
    .setAppName(appId)
    .setMaster(master)
    .set("spark.yarn.app.id", appId + "_" + System.currentTimeMillis() / 1000)
    .set("spark.sql.parquet.compression.codec", "snappy")

  lazy val sc: SparkContext = new SparkContext(sparkConf)
  lazy val sqlCtx: SQLContext = new SQLContext(sc)

}
