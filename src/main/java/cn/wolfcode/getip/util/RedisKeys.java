package cn.wolfcode.getip.util;

import lombok.Getter;

@Getter
public enum RedisKeys {
    IP_KEY("ip_key", 60*60*24);

    private String prifx;
    private int time;

    private RedisKeys(String prifx, int time) {
        this.prifx = prifx;
        this.time=time;
    }

    public String join(String... values) {
        StringBuilder sb = new StringBuilder();
        sb.append( this.prifx );
        for (String value : values) {
            sb.append( ":" ).append( value );
        }
        return sb.toString();

    }
}
