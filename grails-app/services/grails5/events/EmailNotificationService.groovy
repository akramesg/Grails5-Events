package grails5.events

import grails.events.annotation.Subscriber

class EmailNotificationService {

    @Subscriber("grails5.events.article_published")
    def notifyBossOfNewArticle(Object eventData) {
        println "Notify Boss by Mail of new Article: " + eventData.toString()
    }
}
