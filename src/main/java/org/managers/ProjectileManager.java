package org.managers;

import org.enemies.Enemy;
import org.helpers.Constants;
import org.helpers.LoadSave;
import org.objects.Projectile;
import org.objects.Tower;
import org.scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.helpers.Constants.Projectiles.*;
import static org.helpers.Constants.Towers.*;

public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] projImgs, explosionImgs;
    private int projectileID;
    private boolean drawExplosion = false;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        projImgs = new BufferedImage[3];
        importImgs();
    }

    private void importImgs() {
        //Загружает картинки всех снарядов
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for (int i = 0; i < 3; i++) {
            projImgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        }
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {
        //А тут анимация взрыва
        explosionImgs = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            explosionImgs[i] = atlas.getSubimage(i * 32, 2 * 32, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e) {
        //Универсальное создание снаряда
        int type = getProjectileType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totalDist = Math.abs(xDist) + Math.abs(yDist);

        float xPercent = (float) Math.abs(xDist) / totalDist;

        float xSpeed = xPercent * Constants.Projectiles.GetSpeed(type);
        float ySpeed = Constants.Projectiles.GetSpeed(type) - xSpeed;

        if (t.getX() > e.getX())
            xSpeed *= -1;

        if (t.getY() > e.getY())
            ySpeed *= -1;

        float rotate = 0;

        if (type == ARROW) {
            //Если стрела, то ещё и вращает её в сторону врага
            float arcValue = (float) Math.atan(yDist / (float) xDist);
            rotate = (float) Math.toDegrees(arcValue);

            if (xDist < 0)
                rotate += 180;
        }
        projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, (int) t.getDmg(), rotate,
                projectileID++, type));
    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjectileHittingEnemy(p)) {
                    p.setActive(false);
                    //Убирает снаряд и включает анимацию взрыва
                    if (p.getProjectileType() == BOMB) {
                        explosions.add(new Explosion(p.getPos()));
                        explodeOnEnemies(p);
                    }
                }
            }
        }
        for (Explosion e : explosions) {
            if (e.getIndex() < 7)
                e.update();
        }
    }

    private void explodeOnEnemies(Projectile p) {
        //Урон по площади
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float radius = 40.0f;

                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());
                float realDist = (float) Math.hypot(xDist, yDist);

                if (realDist <= radius)
                    e.hurt(p.getDmg());
            }
        }
    }

    private boolean isProjectileHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive())
                if (e.getBounds().contains(p.getPos())) {
                    e.hurt(p.getDmg());
                    return true;
                }
        }
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Projectile p : projectiles)
            if (p.isActive()) {
                if (p.getProjectileType() == ARROW) {
                    //Вращение для стрел
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getRotation()));
                    g2d.drawImage(projImgs[p.getProjectileType()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(p.getRotation()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    //Без вращения
                    g2d.drawImage(projImgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
                }
            }
        drawExplosions(g2d);
    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions)
            if (e.getIndex() < 7)
                g2d.drawImage(explosionImgs[e.explosionIndex], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
    }

    private int getProjectileType(Tower t) {
        return switch (t.getTowerType()) {
            case ARCHER -> ARROW;
            case CANNON -> BOMB;
            case WIZARD -> CHAINS;
            default -> 0;
        };
    }

    public class Explosion {
        //Подкласс для взрывов
        private Point2D.Float pos;
        private int explosionTick = 0, explosionIndex = 0;

        public Explosion(Point2D.Float pos) {
            this.pos = pos;
        }

        public void update() {
            explosionTick++;
            if (explosionTick >= 3) {
                explosionTick = 0;
                explosionIndex++;
            }
        }

        public int getIndex() {
            return explosionIndex;
        }

        public Point2D.Float getPos() {
            return pos;
        }
    }
}
