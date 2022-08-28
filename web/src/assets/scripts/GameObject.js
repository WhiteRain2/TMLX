const GAME_OBJECTS = []

export class GameObject {
    constructor() {
        GAME_OBJECTS.push(this);
        this.has_called_start = false;
        this.timedelta = 0;
    }

    start() {}

    update() {}

    destroy() {
        this.on_destroy();
        for (let i in GAME_OBJECTS) {
            if (GAME_OBJECTS[i] === this) {
                GAME_OBJECTS.splice(i, 1);
                break;
            }
        }
    }

    on_destroy() {}
}

let last_timestamp;
const step = (timedelta) => {
    for (let OBJECT of GAME_OBJECTS) {
        if (!OBJECT.has_called_start) {
            OBJECT.has_called_start = true;
            OBJECT.start();
        }
        else {
            OBJECT.timedelta = timedelta - last_timestamp;
            OBJECT.update();
        }
    }
    last_timestamp = timedelta;
    requestAnimationFrame(step); 
}

requestAnimationFrame(step);