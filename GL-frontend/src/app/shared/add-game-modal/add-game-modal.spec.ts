import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGameModal } from './add-game-modal';

describe('AddGameModal', () => {
  let component: AddGameModal;
  let fixture: ComponentFixture<AddGameModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGameModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddGameModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
