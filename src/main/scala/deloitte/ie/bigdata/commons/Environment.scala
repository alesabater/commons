package deloitte.ie.bigdata.commons

import org.apache.log4j.{LogManager, Logger}
import sys.process._
import scala.util.{Failure, Success, Try}

object Environment {

  @transient lazy val log: Logger = LogManager.getLogger(getClass)

  object Env extends Enumeration {
    type Env = Value
    val PROD = Value("prod")
    val TEST = Value("test")
    val DEV = Value("dev")
  }

  def parseEnv(envUrl: String) = {
    log.info("yarnHS is: " + envUrl)
    val pattern = "(?i).*LHANSAHDP(\\d).*".r
    envUrl match {
      case pattern(env) =>
        env match {
          case "2" => Env.PROD
          case "1" => Env.TEST
        }
      case _ => Env.DEV
    }
  }

  def getServiceName(shell: shell = new shell): String = {
    val pattern = "(?i)(?s).*\\s(LHANSAHDP\\d).*".r
    shell.hdfsReportCapture match {
      case pattern(env) => env
      case _ => ""
    }
  }

  val current = parseEnv(getServiceName())
  def master = {log.info("Master is: " + current); current match {
    case Env.PROD => "yarn-cluster"
    case Env.TEST => "yarn-cluster"
    case Env.DEV => "local"}
  }

}

class shell {
  def hdfsReportCapture: String = Try("hdfs dfsadmin -report" #| "grep Hostname" #| "head -n 1" !!) match {
    case Success(s) => s
    case Failure(f) => ""
  }

}
