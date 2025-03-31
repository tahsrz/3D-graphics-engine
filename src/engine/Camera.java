package engine;

import graphicstructs.Math3D;
import mathkit.Matrix;
import mathkit.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera implements KeyListener {
    // Position

    /**
     * Position in the 3D environment
     */
    private Vector worldPos;

    private Matrix rotMat;

     // Speed

    private double moveSpeed;

    private double strafeSpeed;

    private double flySpeed;

    /**
     * The speed at which the camera rotates in radians
     */
    private double rotSpeed;

    // Angles

    /**
     * Rotation angle around the y-axis
     */
    private double yaw;

    /**
     * Rotation angle around the x-axis
     */
    private double pitch;

    /**
     * Rotation angle around the z-axis
     */
    private double roll;

    // Movement

    private boolean movingLeft;

    private boolean movingRight;

    private boolean movingUp;

    private boolean movingDown;

    private boolean movingForward;

    private boolean movingBackward;

    // Panning

    private boolean panningLeft;

    private boolean panningRight;

    private boolean panningUp;

    private boolean panningDown;

    private boolean tiltingLeft;

    private boolean tiltingRight;

    // Constructors

    public Camera(double moveSpeed, double strafeSpeed, double flySpeed, double rotSpeed) {
        this.moveSpeed = moveSpeed;
        this.strafeSpeed = strafeSpeed;
        this.flySpeed = flySpeed;
        this.rotSpeed = rotSpeed;

        worldPos = new Vector(3);

        rotMat = Matrix.identityMatrix(3);
    }

    public void update() {
        if (panningLeft || panningRight || panningUp || panningDown || tiltingLeft || tiltingRight) {
            rotate();
            rotMat = Math3D.rotationMatrix(yaw, pitch, roll);
        }


        move();
    }

    private void rotate() {
        if (panningLeft) {
            yaw -= rotSpeed;
        }

        if (panningRight) {
            yaw += rotSpeed;
        }

        if (panningUp) {
            pitch -= rotSpeed;
        }

        if (panningDown) {
            pitch += rotSpeed;
        }

        if (tiltingLeft) {
            roll -= rotSpeed;
        }

        if (tiltingRight) {
            roll += rotSpeed;
        }
    }

    private void move(double deltaTime) {
        if (movingForward || movingBackward) {
            Vector zTranspose = Math3D.forwardRotated(rotMat).multiplyByScalar(moveSpeed);

            if (movingForward) {
                worldPos = worldPos.add(zTranspose);
            }

            if (movingBackward) {
                worldPos = worldPos.subtract(zTranspose);
            }
        }

        if (movingUp || movingDown) {
            Vector yTranspose = Math3D.upRotated(rotMat).multiplyByScalar(flySpeed);

            if (movingUp) {
                worldPos = worldPos.add(yTranspose);
            }

            if (movingDown) {
                worldPos = worldPos.subtract(yTranspose);
            }
        }

        if (movingLeft || movingRight) {
            Vector xTranspose = Math3D.rightRotated(rotMat).multiplyByScalar(strafeSpeed);

            if (movingLeft) {
                worldPos = worldPos.add(xTranspose);
            }

            if (movingRight) {
                worldPos = worldPos.subtract(xTranspose);
            }
        }
    }

    // Getters

    /**
     * @return the vector position of the camera in the 3D environment
     */
    public Vector getWorldPos() {
        return worldPos;
    }

    /**
     * @return the rotation matrix of the camera
     */
    public Matrix getRotMat() {
        return rotMat;
    }

    /**
     * @return a matrix representing where the camera is pointing towards in world space
     */
    public Matrix getPointAtMat() {
        Vector right = Math3D.rightRotated(rotMat);
        Vector up = Math3D.upRotated(rotMat);
        Vector forward = right.crossProduct(up);

        Matrix pointAtMat = new Matrix(right.size(), right.size());
        for (int i = 0; i < right.size(); i++) {
            pointAtMat.set(0, i, right.get(i));
            pointAtMat.set(1, i, up.get(i));
            pointAtMat.set(2, i, forward.get(i));
        }

        return pointAtMat;
    }

    // Overrides

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
private static final int KEY_MOVE_LEFT = KeyEvent.VK_A;
private static final int KEY_MOVE_RIGHT = KeyEvent.VK_D;
private static final int KEY_MOVE_FORWARD = KeyEvent.VK_W;
private static final int KEY_MOVE_BACKWARD = KeyEvent.VK_S;
private static final int KEY_MOVE_UP = KeyEvent.VK_SPACE;
private static final int KEY_MOVE_DOWN = KeyEvent.VK_CONTROL;
private static final int KEY_PAN_LEFT = KeyEvent.VK_LEFT;
private static final int KEY_PAN_RIGHT = KeyEvent.VK_RIGHT;
private static final int KEY_PAN_UP = KeyEvent.VK_UP;
private static final int KEY_PAN_DOWN = KeyEvent.VK_DOWN;
private static final int KEY_TILT_LEFT = KeyEvent.VK_Q;
private static final int KEY_TILT_RIGHT = KeyEvent.VK_E;


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KEY_MOVE_LEFT) {
            movingLeft = true;
        }
        

        // movement
      

        if (code == KEY_MOVE_RIGHT) {
            movingRight = true;
        }

        if (code == KEY_MOVE_FORWARD) {
            movingForward = true;
        }

        if (code == KEY_MOVE_BACKWARD) {
            movingBackward = true;
        }

        if (code == KEY_MOVE_UP) {
            movingUp = true;
        }

        if (code == KEY_MOVE_DOWN) {
            movingDown = true;
        }

        // rotation
        if (code == KEY_PAN_LEFT) {
            panningLeft = true;
        }

        if (code == KEY_PAN_RIGHT) {
            panningRight = true;
        }

        if (code == KEY_PAN_UP) {
            panningUp = true;
        }

        if (code == KEY_PAN_DOWN) {
            panningDown = true;
        }

        if (code == KEY_TILT_LEFT) {
            tiltingLeft = true;
        }

        if (code == KEY_TILT_RIGHT) {
            tiltingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // movement
        if (code == KEY_MOVE_LEFT) {
            movingLeft = false;
        }

        if (code == KEY_MOVE_RIGHT) {
            movingRight = false;
        }

        if (code == KEY_MOVE_FORWARD) {
            movingForward = false;
        }

        if (code == KEY_MOVE_BACKWARD) {
            movingBackward = false;
        }

        if (code == KEY_MOVE_UP) {
            movingUp = false;
        }

        if (code == KEY_MOVE_DOWN) {
            movingDown = false;
        }

        // rotation
        if (code == KEY_PAN_LEFT) {
            panningLeft = false;
        }

        if (code == KEY_PAN_RIGHT) {
            panningRight = false;
        }

        if (code == KEY_PAN_UP) {
            panningUp = false;
        }

        if (code == KEY_PAN_DOWN) {
            panningDown = false;
        }

        if (code == KEY_TILT_LEFT) {
            tiltingLeft = false;
        }

        if (code == KEY_TILT_RIGHT) {
            tiltingRight = false;
        }
    }
}
