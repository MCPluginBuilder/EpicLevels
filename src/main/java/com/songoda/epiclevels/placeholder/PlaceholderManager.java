package com.songoda.epiclevels.placeholder;

import com.songoda.epiclevels.EpicLevels;
import com.songoda.epiclevels.players.EPlayer;
import com.songoda.epiclevels.utils.Methods;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {
    private final EpicLevels plugin;

    public PlaceholderManager(EpicLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        EPlayer ePlayer = this.plugin.getPlayerManager().getPlayer(player);
        final String[] split = params.split("_");
        final String format = split.length > 1 ? split[1] : null;

        switch (split.length <= 1 ? params : split[0]) {
            case "level":
                return Methods.formatDecimal(ePlayer.getLevel(), format);
            case "experience":
                return Methods.formatDecimal(ePlayer.getExperience(), format);
            case "kills":
                return Methods.formatDecimal(ePlayer.getKills(), format);
            case "playerkills":
                return Methods.formatDecimal(ePlayer.getPlayerKills(), format);
            case "mobkills":
                return Methods.formatDecimal(ePlayer.getMobKills(), format);
            case "deaths":
                return Methods.formatDecimal(ePlayer.getDeaths(), format);
            case "killstreak":
                return Methods.formatDecimal(ePlayer.getKillStreak(), format);
            case "bestkillstreak":
                return Methods.formatDecimal(ePlayer.getBestKillStreak(), format);
            case "kdr":
                return Methods.formatDecimal(ePlayer.getDeaths() == 0 ? ePlayer.getPlayerKills() : (double) ePlayer.getPlayerKills() / (double) ePlayer.getDeaths(), format);
            case "nextlevel":
                return Methods.formatDecimal(ePlayer.getLevel() + 1, format);
            case "neededfornextlevel":
                return Methods.formatDecimal(EPlayer.experience(ePlayer.getLevel() + 1) - ePlayer.getExperience(), format);
            case "boosterenabled":
                return this.plugin.getBoostManager().getBoost(ePlayer.getUniqueId()) == null
                        ? this.plugin.getLocale().getMessage("general.word.enabled").toText()
                        : this.plugin.getLocale().getMessage("general.word.disabled").toText();
            case "booster":
                if (this.plugin.getBoostManager().getBoost(ePlayer.getUniqueId()) == null) {
                    return "1";
                }
                return Methods.formatDecimal(this.plugin.getBoostManager().getBoost(ePlayer.getUniqueId()).getMultiplier(), format);
            case "globalboosterenabled":
                return this.plugin.getBoostManager().getGlobalBoost() == null
                        ? this.plugin.getLocale().getMessage("general.word.enabled").toText()
                        : this.plugin.getLocale().getMessage("general.word.disabled").toText();
            case "globalbooster":
                if (this.plugin.getBoostManager().getGlobalBoost() == null) {
                    return "1";
                }
                return Methods.formatDecimal(this.plugin.getBoostManager().getGlobalBoost().getMultiplier(), format);
            case "progressbar":
                double exp = ePlayer.getExperience() - EPlayer.experience(ePlayer.getLevel());
                double nextLevel = EPlayer.experience(ePlayer.getLevel() + 1) - EPlayer.experience(ePlayer.getLevel());
                return Methods.generateProgressBar(exp, nextLevel, true);
            default:
                return null;
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "epiclevels";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Songoda";
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }
}
