package online.wyi.customizethreadpool;

import com.alibaba.fastjson.JSON;
import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Component
public class AsyncTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("myTaskAsyncPool")
    public void doTask1(int i) {
        logger.info("Task" + i + " started.");
    }

    @Async
    public void doTask2(TaskBO taskBO) throws InterruptedException {
        logger.info("Task2-Native" + taskBO.getCode() + " " + taskBO.getName() + " started.");
    }

    private static final String ROOM = "爱情放映厅";

    private static final int ROW = 10;

    private static final int COL = 20;

    private static final String FILM_NAME = "怦然心动";

    private static final BigDecimal PRICE = BigDecimal.valueOf(30);

    private static Vector<Ticket> tickets = new Vector<>();

    private static int customerId = 1;

    private static Vector<Customer> customers = new Vector<>();

    private boolean isEnd = false;

    static {
        //爱情放映厅总共有250个座位，2020-05-20 13:14 放映怦然心动，售票价格为30元
        int ticketId = 1;
        for (int i = 1; i <= ROW; i++) {
            for (int j = 1; j <= COL; j++) {
                Ticket ticket = new Ticket(ticketId++, ROOM, i, j, FILM_NAME, PRICE, LocalDateTime.of(2020, 5, 20, 13, 14), null);
                tickets.add(ticket);
            }
        }
        //顾客到售票点进行随机买票
        Collections.shuffle(tickets);
    }

    @Async
    public void sellTickets(String windowName, int customerCount) throws InterruptedException {
        int half = customerCount / 2;
  /*      while (tickets.size() > 0 && customerId <= customerCount) {
            synchronized (AsyncTask.class) {
                if (tickets.isEmpty() || customerId > customerCount) {
                    return;
                }
                logger.info("MyExecutor2 -" + Thread.currentThread().getName() + " is running");
                Ticket ticket = tickets.get(tickets.size() - 1);
                ticket.setWindow(windowName);
                Customer customer = new Customer(customerId);
                customer.buyTicket(ticket);
                customers.add(customer);
                tickets.remove(ticket);
                String msg = StringFormatter.format("卖出第%s张,剩余%s张,%s", customerId++, tickets.size(), customer.getTicket().toString())
                        .getValue();
                logger.info(msg);
                Thread.sleep(30);
            }
        }*/
        while (tickets.size() > 0 && customerId <= half) {
            synchronized (AsyncTask.class) {
                if (tickets.isEmpty() || customerId > half) {
                    return;
                }
                Ticket ticket = tickets.get(tickets.size() - 1);
                ticket.setWindow(windowName);
                Customer customer = new Customer(customerId);
                customer.buyTicket(ticket);
                customers.add(customer);
                tickets.remove(ticket);
                String msg = StringFormatter.format("卖出第%s张,剩余%s张,%s", customerId++, tickets.size(), customer.getTicket().toString())
                        .getValue();
                logger.info(msg);
                Thread.sleep(30);
            }
        }
        while (tickets.size() > 0 && customerId <= customerCount && customerId > half) {
            synchronized (AsyncTask.class) {
                if (tickets.isEmpty() || customerId > customerCount) {
                    return;
                }
                Ticket ticket = tickets.get(tickets.size() - 1);
                ticket.setWindow(windowName);
                Customer customer = new Customer(customerId);
                customer.buyTicket(ticket);
                customers.add(customer);
                tickets.remove(ticket);
                String msg = StringFormatter.format("卖出第%s张,剩余%s张,%s", customerId++, tickets.size(), customer.getTicket().toString())
                        .getValue();
                logger.info(msg);
                Thread.sleep(30);
            }
        }
        if (!isEnd && (tickets.isEmpty() || customerId >= customerCount)) {
            synchronized (AsyncTask.class) {
                if (isEnd) {
                    return;
                }
                System.out.println("电影票出售情况如下：");
                System.out.println("总共票数:" + ROW * COL + ",剩余票数：" + tickets.size());
                System.out.println("买到票的人数：" + customers.size());
                System.out.println("未买到票的人数：" + (customerCount - customers.size()));
                List<Ticket> ticketList = customers.stream().map(Customer::getTicket).collect(Collectors.toList());
                List<String> windowsList = ticketList.stream().map(Ticket::getWindow).distinct().sorted().collect(Collectors.toList());
                logger.info("sell ticketIdList :" + ticketList.size() + "   detail : " + JSON.toJSONString(ticketList.stream().map(Ticket::getTicketId).sorted().collect(Collectors.toList())));
                logger.info("-----------------------------------------------------------------------------------------------------------------------");
                logger.info("sell windowsList :" + windowsList.size() + "   detail : " + JSON.toJSONString(windowsList));
                logger.info("-----------------------------------------------------------------------------------------------------------------------");
                logger.info("left ticketIdList :" + tickets.size() + "   detail : " + JSON.toJSONString(tickets.stream().map(Ticket::getTicketId).sorted().collect(Collectors.toList())));
                isEnd = true;
            }
        }
    }
}
