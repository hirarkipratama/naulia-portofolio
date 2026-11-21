package id.naulia.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import id.naulia.portfolio.di.AppContainer
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.navigation.Navigator
import id.naulia.portfolio.navigation.SectionScrollCoordinator
import id.naulia.portfolio.navigation.setupScrollObserver
import id.naulia.portfolio.ui.components.BottomTabBar
import id.naulia.portfolio.ui.components.TopNavBar
import id.naulia.portfolio.ui.screens.AboutScreen
import id.naulia.portfolio.ui.screens.AudioReelScreen
import id.naulia.portfolio.ui.screens.ContactScreen
import id.naulia.portfolio.ui.screens.EditorialWorksScreen
import id.naulia.portfolio.ui.screens.FooterSection
import id.naulia.portfolio.ui.screens.HeroScreen
import id.naulia.portfolio.ui.screens.McStageScreen
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LuminaTheme
import id.naulia.portfolio.ui.theme.rememberIsMobile

@Composable
fun App() {
    LuminaTheme {
        val scrollCoordinator = remember { SectionScrollCoordinator() }
        val navigator = remember(scrollCoordinator) { Navigator(scrollCoordinator) }
        val cmsViewModel = remember { AppContainer.cmsViewModel }
        val audioViewModel = remember { AppContainer.audioViewModel }
        val contactViewModel = remember { AppContainer.contactViewModel }
        val activeSection by navigator.activeSection.collectAsState()
        val isMobile = rememberIsMobile()

        LaunchedEffect(Unit) {
            setupScrollObserver(navigator)
        }
        LaunchedEffect(scrollCoordinator, navigator) {
            snapshotFlow { scrollCoordinator.scrollState.value }
                .collect { position -> navigator.onScrollPositionChanged(position) }
        }
        DisposableEffect(navigator) {
            onDispose { navigator.onCleared() }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollCoordinator.scrollState),
            ) {
                Spacer(Modifier.height(LuminaDimens.size.topBarHeight))
                SectionAnchor(NavSection.Home, scrollCoordinator) {
                    HeroScreen(navigator = navigator)
                }
                SectionAnchor(NavSection.Audio, scrollCoordinator) {
                    AudioReelScreen(audioViewModel = audioViewModel)
                }
                SectionAnchor(NavSection.Books, scrollCoordinator) {
                    EditorialWorksScreen(cmsViewModel = cmsViewModel)
                }
                SectionAnchor(NavSection.Stage, scrollCoordinator) {
                    McStageScreen(cmsViewModel = cmsViewModel)
                }
                SectionAnchor(NavSection.About, scrollCoordinator) {
                    AboutScreen()
                }
                SectionAnchor(NavSection.Contact, scrollCoordinator) {
                    ContactScreen(contactViewModel = contactViewModel)
                }
                FooterSection(navigator = navigator)
                if (isMobile) {
                    Spacer(Modifier.height(LuminaDimens.size.bottomBarHeight))
                }
            }

            TopNavBar(
                activeSection = activeSection,
                onSectionClick = { section -> navigator.navigateTo(section) },
                isCompact = isMobile,
                modifier = Modifier.align(Alignment.TopCenter),
            )
            if (isMobile) {
                BottomTabBar(
                    activeSection = activeSection,
                    onSectionClick = { section -> navigator.navigateTo(section) },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
private fun SectionAnchor(
    section: NavSection,
    coordinator: SectionScrollCoordinator,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val offsetPx = coordinates.positionInRoot().y + coordinator.scrollState.value
                coordinator.registerSectionIfAbsent(
                    sectionId = section.sectionId,
                    offsetPx = offsetPx.toInt(),
                )
            },
    ) {
        content()
    }
}
