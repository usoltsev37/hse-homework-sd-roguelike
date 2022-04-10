package ru.hse.roguelike.model.items

import kotlinx.serialization.Serializable
import ru.hse.roguelike.model.characters.Hero
import kotlin.random.Random


/**
 * Item which can be consumed.
 * @see Item
 * @see ItemType
 */
@Serializable
class ConsumableItem(
    override val name: String,
    override val itemType: ItemType
) : Item() {

    private val healthAmount: Int = Random.nextInt(1, 11)

    override val description: String
        get() = "healthAmount = $healthAmount"

    override fun use(hero: Hero) {
        hero.health += healthAmount
        hero.inventory.remove(this)
    }
}