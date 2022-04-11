package com.testme.feedpresentation.characterList.reducer

import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feedpresentation.characterDetail.model.CharacterDetailSideEffect
import com.testme.feedpresentation.characterList.model.CharactersFeedAction
import com.testme.feedpresentation.characterList.model.CharactersFeedEffect
import com.testme.feedpresentation.characterList.model.CharactersFeedViewEvent
import com.testme.feedpresentation.characterList.model.CharactersFeedViewState
import com.testme.presentation.model.Actor
import com.testme.presentation.model.CoroutineActor
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * An implementation of the [Actor] that exposes a [CoroutineScope] for coroutines launching.
 *
 * @param mainContext a [CoroutineContext] to be used by the exposed [CoroutineScope]
 */
class CharactersActor @Inject constructor(
  private val context: CoroutineContext,
  private val getCharactersUseCase: FlowUseCase<Unit, List<Character>>,
) :
  CoroutineActor<CharactersFeedViewState, CharactersFeedViewEvent, CharactersFeedAction, CharactersFeedEffect>(
    context
  ) {

  override fun handle(
    state: CharactersFeedViewState,
    event: CharactersFeedViewEvent,
    effects: ((CharactersFeedEffect) -> Unit)?,
    actions: ((CharactersFeedAction) -> Unit)?
  ) {

    when (event) {
      is CharactersFeedViewEvent.LoadAllCharactersFeed -> {
        launch {
          getCharactersUseCase(Unit).collect {
            when (it) {
              is DataSourceResult.Error -> {
                effects?.invoke(
                  if (it.data.isNullOrEmpty())
                    CharactersFeedEffect.ShowErrorWithoutData(it.error)
                  else
                    CharactersFeedEffect.ShowErrorWithOutdatedData(it.error)
                )
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
}