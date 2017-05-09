package deloitte.ie.bigdata.commons

import com.typesafe.config.{Config, ConfigFactory}
import deloitte.ie.bigdata.commons.Environment.Env

object LoadedProperties {

  lazy val masterConf = ConfigFactory.load()
  lazy val conf = masterConf.withFallback(ConfigFactory.parseResources(envSpecific)).resolve()

  def envSpecific = {
    lazy val env = Environment.current
    "conf/" + env + "/app.conf"
  }

  def getConfig(env: Env.Value): Config = {
    conf.getConfig(env.toString)
  }

}
