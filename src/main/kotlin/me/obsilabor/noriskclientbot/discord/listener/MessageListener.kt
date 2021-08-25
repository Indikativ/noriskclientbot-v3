package me.obsilabor.noriskclientbot.discord.listener

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.ban
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import me.obsilabor.noriskclientbot.config.ConfigManager
import me.obsilabor.noriskclientbot.detection.MassPingDetection.getPingCount
import me.obsilabor.noriskclientbot.extensions.emojiGuild

class MessageListener : Listener {

    override fun register(client: Kord) {
        client.on<MessageCreateEvent> {
            if(this.message.getPingCount() > 4) {
                this.message.channel.createMessage("Stop mass pinging!")
                this.message.delete()
                this.member?.ban {
                    deleteMessagesDays = 7
                    reason = "Massive pings"
                }
            }
            if(this.message.content.lowercase().contains("hast du eig ein bisschen obsi für mich")) {
                this.message.channel.createMessage(emojiGuild().getEmoji(Snowflake(ConfigManager.noRiskClientBotConfig.emoteServerConfig.obsiEmoteId ?: error("Obsi emoji id is null!"))).mention)
            }
            if(this.message.content.contains("xD")) {
                this.message.addReaction(emojiGuild().getEmoji(Snowflake(ConfigManager.noRiskClientBotConfig.emoteServerConfig.twelveEmoteId ?: error("Twelve emoji id is null!"))))
            }
        }
    }
}