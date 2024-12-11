module com.game {
    requires transitive javafx.controls;
    requires javafx.graphics;

    exports com.game;

    opens com.game.model to javafx.base;
}
