import SvgColor from '../../../components/svg-color';

const icon = (name) => <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />;

const navConfig = [
  {
    title: 'wallet',
    path: '/wallet',
    icon: icon('ic_wallet'),
  },
  {
    title: 'cards',
    path: '/cards',
    icon: icon('ic_card'),
  },
  {
    title: 'transfers',
    path: '/transfers',
    icon: icon('ic_transfer'),
  },
  {
    title: 'transactions',
    path: '/transactions',
    icon: icon('ic_transaction'),
  }
];

export default navConfig;
