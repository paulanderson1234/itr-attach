/*
 * Copyright 2017 HM Revenue & Customs
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

package repositories

import models.ValidationError
import reactivemongo.api.DB
import reactivemongo.api.indexes.{Index, IndexType}
import reactivemongo.bson._
import uk.gov.hmrc.mongo.json.ReactiveMongoFormats
import uk.gov.hmrc.mongo.{ReactiveRepository, Repository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait InvestorErrorRepository extends Repository[ValidationError, BSONObjectID]{
}

class InvestorErrorMongoRepository(implicit mongo: () => DB)
  extends ReactiveRepository[ValidationError, BSONObjectID]("investorErrors", mongo, ValidationError.format, ReactiveMongoFormats.objectIdFormats)
    with InvestorErrorRepository {

  override def indexes = Seq(
    Index(Seq("aError" -> IndexType.Ascending), name = Some("aErrorUniqueIdx"), unique = true, sparse = true)
  )

  def createError(error: ValidationError): Unit = {
    insert(error)
  }

  def dropCollection: Future[Unit] = collection.drop()
}