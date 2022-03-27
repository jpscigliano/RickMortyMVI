package com.testme.feedpresentation.characterList.reducer

import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feedpresentation.characterList.model.CharactersFeedAction
import com.testme.feedpresentation.characterList.model.CharactersFeedEffect
import com.testme.feedpresentation.characterList.model.CharactersFeedViewEvent
import com.testme.feedpresentation.characterList.model.CharactersFeedViewState
import com.testme.presentation.model.Actor
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * An implementation of the [Actor] that exposes a [CoroutineScope] for coroutines launching.
 *
 * @param mainContext a [CoroutineContext] to be used by the exposed [CoroutineScope]
 */
class CharactersActor @Inject constructor(
  private val getCharactersUseCase: FlowUseCase<Unit, List<Character>>,
  private val mainContext: CoroutineContext = Dispatchers.Main
) :
  Actor<CharactersFeedViewState, CharactersFeedViewEvent, CharactersFeedAction, CharactersFeedEffect> {

  /**
   * A [CoroutineScope] that can be used by the [CharactersActor] to launch coroutines.
   * The [CoroutineScope] is automatically cancelled on dispose.
   */
  private val scope: CoroutineScope = CoroutineScope(mainContext)

  private var job: Job? = null

  override fun handle(
    stateFeed: CharactersFeedViewState,
    event: CharactersFeedViewEvent,
    effects: ((CharactersFeedEffect) -> Unit)?,
    actions: ((CharactersFeedAction) -> Unit)?,
  ) {

    when (event) {
      is CharactersFeedViewEvent.LoadAllCharactersFeed -> {
        job?.cancel()
        job = scope.launch {
          getCharactersUseCase(Unit).collect {
            when (it) {
              is DataSourceResult.Error -> {
                effects?.invoke(CharactersFeedEffect.ShowNoDataError)
                actions?.invoke(CharactersFeedAction.FinishLoadingCharactersFeed)
              }
              is DataSourceResult.InProgress -> {
                actions?.invoke(CharactersFeedAction.LoadingCharactersFeed)
              }
              is DataSourceResult.NoInternet -> {
                effects?.invoke(CharactersFeedEffect.ShowNoInternetError)
                actions?.invoke(CharactersFeedAction.FinishLoadingCharactersFeed)
              }
              is DataSourceResult.Success -> {
                actions?.invoke(CharactersFeedAction.CharactersFeedLoaded(it.data))
                actions?.invoke(CharactersFeedAction.FinishLoadingCharactersFeed)
              }
            }
          }
        }
      }
      is CharactersFeedViewEvent.SelectCharacter -> {
        actions?.invoke(CharactersFeedAction.SelectingCharacter(event.id))
        effects?.invoke(CharactersFeedEffect.NavigateToCharacterDetail(event.id))
      }
    }
  }

  override fun dispose() {
    scope.cancel()
  }
}