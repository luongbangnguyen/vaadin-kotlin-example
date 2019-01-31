package com.vaadin.example.domain

import org.elasticsearch.common.settings.Settings
import org.elasticsearch.node.InternalSettingsPreparer
import org.elasticsearch.node.Node
import org.elasticsearch.plugins.Plugin

class ElasticsearchEmbed(preparedSettings: Settings, classpathPlugins: Collection<Class<out Plugin>>) : Node(InternalSettingsPreparer.prepareEnvironment(preparedSettings, null), classpathPlugins)
