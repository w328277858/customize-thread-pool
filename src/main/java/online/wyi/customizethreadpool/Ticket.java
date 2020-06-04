package online.wyi.customizethreadpool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 通过卖票程序读懂多线程--电影票的类
 *
 * @author wyi
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    /**
     * ticketId
     */
    private Integer ticketId;

    /**
     * 放映厅
     */
    private String  room;

    /**
     * 行
     */
    private Integer row;

    /**
     * 列
     */
    private Integer col;

    /**
     * 电影名
     */
    private String filmName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 放映时间
     */
    private LocalDateTime datetime;

    /**
     * 买票窗口
     */
    private String window;

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", row=" + row +
                ", col=" + col +
                ", window='" + window + '\'' +
                '}';
    }
}