import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product.reducer';
// prettier-ignore
import image, {
  ImageState
} from 'app/entities/image/image.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import productInfo, {
  ProductInfoState
} from 'app/entities/product-info/product-info.reducer';
// prettier-ignore
import serviceOpt, {
  ServiceOptState
} from 'app/entities/service-opt/service-opt.reducer';
// prettier-ignore
import code, {
  CodeState
} from 'app/entities/code/code.reducer';
// prettier-ignore
import behaviorRecording, {
  BehaviorRecordingState
} from 'app/entities/behavior-recording/behavior-recording.reducer';
// prettier-ignore
import comment, {
  CommentState
} from 'app/entities/comment/comment.reducer';
// prettier-ignore
import userX, {
  UserXState
} from 'app/entities/user-x/user-x.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly product: ProductState;
  readonly image: ImageState;
  readonly location: LocationState;
  readonly productInfo: ProductInfoState;
  readonly serviceOpt: ServiceOptState;
  readonly code: CodeState;
  readonly behaviorRecording: BehaviorRecordingState;
  readonly comment: CommentState;
  readonly userX: UserXState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  product,
  image,
  location,
  productInfo,
  serviceOpt,
  code,
  behaviorRecording,
  comment,
  userX,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
