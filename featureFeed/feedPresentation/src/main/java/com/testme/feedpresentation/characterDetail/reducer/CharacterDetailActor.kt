package com.testme.feedpresentation.characterDetail.reducer

import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feedpresentation.characterDetail.model.CharacterDetailAction
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewEvent
import com.testme.feedpresentation.characterDetail.model.CharacterDetailSideEffect
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewState
import com.testme.presentation.model.Actor
import com.testme.presentation.model.CoroutineActor
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext

class CharacterDetailActor(
  private val context: CoroutineContext,
  private val getCharacterUseCase: FlowUseCase<Id, Character>
) :
  CoroutineActor<CharacterDetailViewState, CharacterDetailViewEvent, CharacterDetailAction, CharacterDetailSideEffect>(
    context
  ) {

  override fun handle(
    state: CharacterDetailViewState,
    event: CharacterDetailViewEvent,
    effects: ((CharacterDetailSideEffect) -> Unit)?,
    actions: ((CharacterDetailAction) -> Unit)?
  ) {
    when (event) {
      is CharacterDetailViewEvent.LoadCharacterDetailView -> {
        if (event.id ==null ||event.id.invoke()==0) {
          actions?.invoke(CharacterDetailAction.NoCharacterSelected)
        } else {
          launch {
            getCharacterUseCase(event.id).collectLatest { result ->
              when (result) {
                is DataSourceResult.Error -> {
                  if (result.data == null) {
                    effects?.invoke(CharacterDetailSideEffect.ShowErrorWithoutData)
                    actions?.invoke(CharacterDetailAction.NoCharacterSelected)
                  } else {
                    effects?.invoke(CharacterDetailSideEffect.ShowErrorWithoutData)
                    actions?.invoke(CharacterDetailAction.FinishLoadingCharacterDetail)
                  }
                }
                is DataSourceResult.NoInternet -> {
                  effects?.invoke(CharacterDetailSideEffect.ShowNoInternetError)
                  actions?.invoke(CharacterDetailAction.FinishLoadingCharacterDetail)
                }
                is DataSourceResult.InProgress -> {
                  actions?.invoke(CharacterDetailAction.LoadingCharacterDetail(event.id))
                }
                is DataSourceResult.Success -> {
                  actions?.invoke(CharacterDetailAction.CharacterDetailLoaded(result.data))
                }
              }
            }
          }
        }
      }
    }
  }
}
