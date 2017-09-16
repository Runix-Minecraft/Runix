package com.newlinegaming.Runix.rune;

//TODO reimplement when greek fire is added back

//public class GreekFireRune extends AbstractRune {
//
//    public GreekFireRune(){
//        runeName = "Greek Fire";
//        MinecraftForge.EVENT_BUS.register(this);
//    }
//
//    @Override
//    public Block[][][] runicTemplateOriginal() {
//        Block FENC = Blocks.iron_bars;
//        Block LAPS = Blocks.lapis_block;
//        return new Block[][][]
//                {{{TIER,FENC,TIER},
//                  {FENC,LAPS,FENC},
//                  {TIER,FENC,TIER}}};
//    }
//
//    @SubscribeEvent
//    public void onBlockPlace(PlayerInteractEvent event) {
//        if(!event.entityPlayer.worldObj.isRemote) {
//            if (event.action == Action.RIGHT_CLICK_BLOCK){  // && event.action != Action.RIGHT_CLICK_AIR) {
//                WorldXYZ target = new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z);
//                target = target.offset(Vector3.facing[event.face]);
//
//                // only accept fuel if there is not still lifespan remaining in the fire.  Do not allow pumping free fuel or block dupe bug
//                if(target.getBlock().equals(ModBlock.greekFire) &&
//                        (target.getMetaId() == 15 || target.offset(Vector3.DOWN).getBlock().equals(Blocks.lapis_block))) {
//                    ItemStack blockUsed = event.entityPlayer.getCurrentEquippedItem();
//                    if(blockUsed != null) {
//                        Block block = Block.getBlockFromItem(blockUsed.getItem());
//                        if(GreekFire.consumeValuableForFuel(target, block)) {
//                            event.setCanceled(true);
//                            blockUsed.stackSize -= 1;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean isFlatRuneOnly() {
//        return true;
//    }
//
//    @Override
//    public void execute(WorldXYZ coords, EntityPlayer player) {
//        accept(player);
//        consumeRune(coords);
//        coords.setBlockIdAndUpdate(Blocks.lapis_block); // this just got consumed
//        int newLife = Math.max(15 - Tiers.energyToRadiusConversion(energy - Tiers.getEnergy(Blocks.lapis_block),
//                Tiers.blockBreakCost), 0); //radius calculation
//        HashSet<WorldXYZ> shell = UtilSphericalFunctions.getShell(coords, 1);
//        for(WorldXYZ point : shell){
//            point.setBlock(ModBlock.greekFire, newLife);
//        }
//    }
//
//}
