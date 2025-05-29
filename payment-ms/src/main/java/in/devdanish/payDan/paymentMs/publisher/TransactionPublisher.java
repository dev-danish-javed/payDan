package in.devdanish.payDan.paymentMs.publisher;

import in.devdanish.payDan.paymentMs.model.Transaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionPublisher {
    private final Map<String, FluxSink<Transaction>> subscribers = new ConcurrentHashMap<>();
    public void publish(String walletId, Transaction transaction){
        FluxSink<Transaction> sink = subscribers.get(walletId);
        if(sink != null){
            sink.next(transaction);
        }
    }
    public Flux<Transaction> subscribe(String walletId){
        return Flux.<Transaction>create(sink -> subscribers.put(walletId, sink))
                .doFinally(signalType -> subscribers.remove(walletId));
    }
}
