package studios.bluemoon.keybindings;

public interface Key {

    int getKey();
    String getName();

    void releaseAction();
    void pressAction();
    void holdAction();
    default void onScrollUp() {

    }
    default void onScrollDown() {

    }

}
