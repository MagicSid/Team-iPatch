/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;
import java.io.IOException;

/**
 *
 * @author cpl512
 */
public class ShooterControl extends AbstractControl {
    Vector3f direction;
    int damage;
    boolean isEnemy;
    private static final Sphere sphere;
    SimpleApplication app;
    private BulletControl bulletControl;
    PhysicsSpace physicsSpace;
    PlayerControlState playerControlState;
    
    static {
        sphere = new Sphere(8, 8, 0.4f, true, false);
    }
    
    public ShooterControl(Vector3f direction, boolean isEnemy, SimpleApplication app) {
        this.direction = direction;
        this.damage = 5;
        this.isEnemy = isEnemy;
        this.app = (SimpleApplication)app;
        this.physicsSpace = app.getStateManager().getState(BulletAppState.class)
                                                             .getPhysicsSpace();
        playerControlState = app.getStateManager().getState(PlayerControlState.class);
    }
    
    public void shootBullet(){    
        Geometry bullet_geo = new Geometry("cannon ball", sphere);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        bullet_geo.setMaterial(mat);
        app.getRootNode().attachChild(bullet_geo);
	bullet_geo.setLocalTranslation(spatial.getWorldTranslation().add(
                                       direction.mult(1f).add(0, 1f, 0))); 
        // add(0,1f,0) raises bullet off floor
        // addition is required for the bullets not to spawn underneath ship
	RigidBodyControl bullet_phys = new RigidBodyControl(2f);
	bullet_geo.addControl(bullet_phys);
	physicsSpace.add(bullet_phys);
	bullet_phys.setKinematic(true);
	bulletControl = new BulletControl(direction, damage, isEnemy, physicsSpace,
                                          bullet_phys, playerControlState);
        bullet_geo.addControl(bulletControl);
    }
    
    public void shootBullet(Vector3f direction){
        // For shooters that cannot pass a reference to their direction
        Geometry bullet_geo = new Geometry("cannon ball", sphere);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        bullet_geo.setMaterial(mat);
        app.getRootNode().attachChild(bullet_geo);
	bullet_geo.setLocalTranslation(spatial.getWorldTranslation().add(
                                       direction.mult(1f).add(0, 1f, 0)));
        // add(0,1f,0) raises bullet off floor
        // addition is required for the bullets not to spawn underneath
	RigidBodyControl bullet_phys = new RigidBodyControl(2f);
	bullet_geo.addControl(bullet_phys);
	physicsSpace.add(bullet_phys);
	bullet_phys.setKinematic(true);
	bulletControl = new BulletControl(direction, damage, isEnemy, physicsSpace,
                                          bullet_phys, playerControlState);
        bullet_geo.addControl(bulletControl);
    }
    
    public int getDamage(){
        return this.damage;
    }
    
    public void setDamage(int dmg){
        this.damage = dmg;
    }
    
    @Override
    protected void controlUpdate(float tpf) {}
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
