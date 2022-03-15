import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import grails5.events.Article
import grails5.events.EventHandlingService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
class SimpleTestSpec extends Specification {
    @Autowired
    EventHandlingService service

    void "fire the events"() {
        given:
        Article article = new Article()
        article.headline = "NOTDONE"
        when:
        println "sending event"
        service.event("article_published", article, [ fork: false, namespace: 'grails5.events', onError: { reply ->
            println('some reply')
        } ] )

        println "after sending event"
        then:
        article.headline == "DONE"
    }
}