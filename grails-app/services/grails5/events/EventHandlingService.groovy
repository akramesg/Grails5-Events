package grails5.events

import grails.events.EventPublisher

import java.util.concurrent.CompletableFuture

class EventHandlingService implements EventPublisher {
    final CompletableFuture<String> waitFor = new CompletableFuture<>()

    // Discover how to replicate the EventReply response functionality
    // replaces previous functionality provided by grails events plugin.
    def event(String topic, Object initialParameter, Map eventParams) {
        log.debug("Publishing to topic $topic")

        // join namespace and topic to a single String.
        String newTopic = eventParams?.namespace+ '.' + topic
        if (newTopic.startsWith('.'))
            newTopic = topic

        println("Sending to topic $newTopic")
        // if fork is explicitly set to false
        if (eventParams?.fork == false) {
            try {
                println "b4 sAndR"
                Closure c =  {Object result ->
                    log.debug "in sAndR result"
                    println "in sAndR result"
                    waitFor.complete(result)
                    return result
                }
                sendAndReceive(newTopic, initialParameter,c)
                c
                println "after sAndR"
            } catch (Exception ex) {
                log.error("Error in sendAndReceive call",ex)
                if (eventParams.onError) {
                    Closure callable = (Closure) eventParams.onError
                    callable()
                }
            }
        } else {
            try {
                println('no fork so just notify')
                notify(newTopic, initialParameter)
            } catch (Exception ex) {
                log.error("Error in notify call", ex)
                if (eventParams.onError) {
                    Closure callable = (Closure) eventParams.onError
                    callable()
                }
            }
        }

    }
}



