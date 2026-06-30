//
//  AppDelegate.swift
//  iosApp
//
//  Created by Антон Логвинов on 28.06.2026.
//
import SwiftUI
import Shared

class AppDelegate: NSObject, UIApplicationDelegate {
    let rootComponent: RootComponent = DefaultRootComponent(
        componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle()),
        appGraph: MainViewControllerKt.iosAppGraph
    )
}
