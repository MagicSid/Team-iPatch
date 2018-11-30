package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private BulletAppState bulletAppState;
    BetterCharacterControl character;
    Node ship;
    Node sceneNode;
    
    RigidBodyControl terrainPhysicsNode;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
	//app.getStateManager().attach(new ControlState());
	
    }

    @Override
    public void simpleInitApp() {
	flyCam.setEnabled(false);
	bulletAppState = new BulletAppState();
	bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
	stateManager.attach(bulletAppState);
	stateManager.attach(new ControlState());
        
	ship = (Node)assetManager.loadModel("Models/pirate-ship-blender-v2/mesh.j3o");
	ship.setLocalTranslation(new Vector3f(0, 3, 0));
	character = new BetterCharacterControl(1.5f, 3f, 10f);
	ship.addControl(character);
	
	sceneNode = (Node)assetManager.loadModel("Scenes/newScene.j3o");
	rootNode.attachChild(sceneNode);
	sceneNode.setName("Scene");
	
	rootNode.attachChild(ship);
	ship.setName("PlayerModel");
	
	RigidBodyControl landscape = new RigidBodyControl(0);
	sceneNode.addControl(landscape);
	
        Box box = new Box(1, 1, 1);
        Geometry box_geom = new Geometry("box", box);
        box_geom.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        box_geom.setLocalTranslation(5, 1, 0);
        rootNode.attachChild(box_geom);
        RigidBodyControl box_phys = new RigidBodyControl(0f);
        box_geom.addControl(box_phys);
        
	bulletAppState.getPhysicsSpace().add(character);
	bulletAppState.getPhysicsSpace().add(landscape);
	bulletAppState.getPhysicsSpace().add(box_phys);
        bulletAppState.setDebugEnabled(true);
    }
	
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}