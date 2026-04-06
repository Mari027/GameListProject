import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameCreationModal } from './game-creation-modal';

describe('GameCreationModal', () => {
  let component: GameCreationModal;
  let fixture: ComponentFixture<GameCreationModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameCreationModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameCreationModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
