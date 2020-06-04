package online.wyi.customizethreadpool;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通过卖票程序读懂多线程--顾客类
 *
 * @author wyi
 */
@Getter
@Setter
@ToString
public class Customer {

    /**
     * 顾客id
     */
    private Integer customerId;

    /**
     * 购买的电影票
     */
    private Ticket ticket;

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    //顾客买票
    public void buyTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}