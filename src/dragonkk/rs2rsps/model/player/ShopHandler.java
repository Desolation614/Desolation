package dragonkk.rs2rsps.model.player;

public class ShopHandler {

    private Player player;
    private int shopid = 0;

    public ShopHandler(Player player) {
        this.player = player;
    }

    public void setShopId(int id) {
        this.shopid = id;
    }

    public int getShopId() {
        return shopid;
    }
}
