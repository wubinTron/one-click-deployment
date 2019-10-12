package needPlanManager;
/**
 * 
 * @author tron
 * 
 */

public class NeedPlan {
    private Integer id;
    private String needPlanNum;//所需配置编码
    private String net_type;//配置网络名称
    private String storage_db_engine;//配置数据库名称
    private Integer port;//端口
    private String poster;//提交人
    private String remark;//备注

    @Override
    public String toString() {
        return "NeedPlan{" +
                "id=" + id +
                ", needPlanNum='" + needPlanNum + '\'' +
                ", net_type='" + net_type + '\'' +
                ", storage_db_engine='" + storage_db_engine + '\'' +
                ", port=" + port +
                ", poster='" + poster + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNeedPlanNum() {
        return needPlanNum;
    }

    public void setNeedPlanNum(String needPlanNum) {
        this.needPlanNum = needPlanNum;
    }


    public String getnet_type() {
        return net_type;
    }

    public void setnet_type(String net_type) {
        this.net_type = net_type;
    }


    public String gestorage_db_engine() {
        return storage_db_engine;
    }

    public void setstorage_db_engine(String storage_db_engine) {
        this.storage_db_engine =storage_db_engine;
    }

    public Integer getport() {
        return port;
    }

    public void setport(Integer port) {
        this.port = port;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public NeedPlan(Integer id, String needPlanNum, String net_type,String storage_db_engine, Integer port, String poster, String remark) {
        this.id = id;
        this.needPlanNum = needPlanNum;
        this.net_type = net_type;
        this.storage_db_engine = storage_db_engine;
        this.port = port;
        this.poster = poster;
        this.remark = remark;
    }

    public NeedPlan() {
    }
}
