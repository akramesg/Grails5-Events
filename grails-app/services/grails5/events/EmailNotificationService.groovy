package grails5.events

import reactor.spring.context.annotation.Consumer
import reactor.spring.context.annotation.Selector

@Consumer
class EmailNotificationService {

    @Selector("grails5.events.article_published")
    def notifyBossOfNewArticle(Object eventData) {
        println "Notify Boss by Mail of new Article: " + eventData.toString()
    }
}
