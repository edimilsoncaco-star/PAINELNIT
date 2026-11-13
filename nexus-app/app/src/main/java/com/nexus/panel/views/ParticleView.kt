package com.nexus.panel.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * ParticleView — partículas vermelhas com linhas se conectando.
 * Funciona sem nenhuma lib externa — Canvas 2D puro.
 */
class ParticleView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(ctx, attrs, defStyle) {

    // ── configurações ──
    private val PARTICLE_COUNT = 55
    private val MAX_DIST       = 180f
    private val SPEED          = 0.6f

    private val paintParticle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF1A1A")   // vermelho neon
        style = Paint.Style.FILL
    }
    private val paintLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#CC0000")
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    data class Particle(
        var x: Float, var y: Float,
        var vx: Float, var vy: Float,
        val radius: Float
    )

    private val particles = mutableListOf<Particle>()
    private var w = 0f
    private var h = 0f
    private var initialized = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
        if (!initialized) {
            repeat(PARTICLE_COUNT) {
                particles += Particle(
                    x      = Random.nextFloat() * w,
                    y      = Random.nextFloat() * h,
                    vx     = (Random.nextFloat() - 0.5f) * SPEED * 2,
                    vy     = (Random.nextFloat() - 0.5f) * SPEED * 2,
                    radius = Random.nextFloat() * 2.5f + 1.5f
                )
            }
            initialized = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        // move partículas
        for (p in particles) {
            p.x += p.vx
            p.y += p.vy
            if (p.x < 0 || p.x > w) p.vx = -p.vx
            if (p.y < 0 || p.y > h) p.vy = -p.vy
        }

        // desenha linhas entre partículas próximas
        for (i in particles.indices) {
            for (j in i + 1 until particles.size) {
                val dx   = particles[i].x - particles[j].x
                val dy   = particles[i].y - particles[j].y
                val dist = sqrt(dx * dx + dy * dy)
                if (dist < MAX_DIST) {
                    val alpha = ((1f - dist / MAX_DIST) * 180).toInt()
                    paintLine.alpha = alpha
                    canvas.drawLine(
                        particles[i].x, particles[i].y,
                        particles[j].x, particles[j].y,
                        paintLine
                    )
                }
            }
        }

        // desenha partículas
        for (p in particles) {
            paintParticle.alpha = 220
            canvas.drawCircle(p.x, p.y, p.radius, paintParticle)
            // brilho suave
            paintParticle.alpha = 60
            canvas.drawCircle(p.x, p.y, p.radius * 2.5f, paintParticle)
            paintParticle.alpha = 220
        }

        postInvalidateOnAnimation()
    }
}
