package com.adisalagic.fuelratsirc;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.BanListEvent;
import org.pircbotx.hooks.events.ChannelInfoEvent;
import org.pircbotx.hooks.events.ConnectAttemptFailedEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.ExceptionEvent;
import org.pircbotx.hooks.events.FingerEvent;
import org.pircbotx.hooks.events.HalfOpEvent;
import org.pircbotx.hooks.events.IncomingChatRequestEvent;
import org.pircbotx.hooks.events.IncomingFileTransferEvent;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.ListenerExceptionEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.ModeEvent;
import org.pircbotx.hooks.events.MotdEvent;
import org.pircbotx.hooks.events.NickAlreadyInUseEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.OpEvent;
import org.pircbotx.hooks.events.OutputEvent;
import org.pircbotx.hooks.events.OwnerEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.PingEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.pircbotx.hooks.events.RemoveChannelBanEvent;
import org.pircbotx.hooks.events.RemoveChannelKeyEvent;
import org.pircbotx.hooks.events.RemoveChannelLimitEvent;
import org.pircbotx.hooks.events.RemoveInviteOnlyEvent;
import org.pircbotx.hooks.events.RemoveModeratedEvent;
import org.pircbotx.hooks.events.RemoveNoExternalMessagesEvent;
import org.pircbotx.hooks.events.RemovePrivateEvent;
import org.pircbotx.hooks.events.RemoveSecretEvent;
import org.pircbotx.hooks.events.RemoveTopicProtectionEvent;
import org.pircbotx.hooks.events.ServerPingEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.events.SetChannelBanEvent;
import org.pircbotx.hooks.events.SetChannelKeyEvent;
import org.pircbotx.hooks.events.SetChannelLimitEvent;
import org.pircbotx.hooks.events.SetInviteOnlyEvent;
import org.pircbotx.hooks.events.SetModeratedEvent;
import org.pircbotx.hooks.events.SetNoExternalMessagesEvent;
import org.pircbotx.hooks.events.SetPrivateEvent;
import org.pircbotx.hooks.events.SetSecretEvent;
import org.pircbotx.hooks.events.SetTopicProtectionEvent;
import org.pircbotx.hooks.events.SocketConnectEvent;
import org.pircbotx.hooks.events.SuperOpEvent;
import org.pircbotx.hooks.events.TimeEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.pircbotx.hooks.events.UnknownEvent;
import org.pircbotx.hooks.events.UserListEvent;
import org.pircbotx.hooks.events.UserModeEvent;
import org.pircbotx.hooks.events.VersionEvent;
import org.pircbotx.hooks.events.VoiceEvent;
import org.pircbotx.hooks.events.WhoisEvent;
import org.pircbotx.hooks.types.GenericCTCPEvent;
import org.pircbotx.hooks.types.GenericChannelEvent;
import org.pircbotx.hooks.types.GenericChannelModeEvent;
import org.pircbotx.hooks.types.GenericChannelModeRecipientEvent;
import org.pircbotx.hooks.types.GenericChannelUserEvent;
import org.pircbotx.hooks.types.GenericDCCEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.hooks.types.GenericUserEvent;
import org.pircbotx.hooks.types.GenericUserModeEvent;

import java.util.ArrayList;

public class IRCAdapter extends ListenerAdapter {
    ArrayList<Sublistener> sublisteners = new ArrayList<>();

    @Override
    public void onEvent(Event event) throws Exception {
        super.onEvent(event);
    }

    @Override
    public void onAction(ActionEvent event) throws Exception {
        super.onAction(event);
    }

    @Override
    public void onBanList(BanListEvent event) throws Exception {
        super.onBanList(event);
    }

    @Override
    public void onChannelInfo(ChannelInfoEvent event) throws Exception {
        super.onChannelInfo(event);
    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        super.onConnect(event);
        for (Sublistener sublistener : sublisteners){
            sublistener.onConnect(event);
        }
    }

    @Override
    public void onConnectAttemptFailed(ConnectAttemptFailedEvent event) throws Exception {
        super.onConnectAttemptFailed(event);
        for (Sublistener sublistener : sublisteners){
            sublistener.onConnectAttemptFailed(event);
        }
    }

    @Override
    public void onDisconnect(DisconnectEvent event) throws Exception {
        super.onDisconnect(event);
        for (Sublistener sublistener : sublisteners){
            sublistener.onDisconnect(event);
        }
    }

    @Override
    public void onException(ExceptionEvent event) throws Exception {
        super.onException(event);
    }

    @Override
    public void onFinger(FingerEvent event) throws Exception {
        super.onFinger(event);
    }

    @Override
    public void onHalfOp(HalfOpEvent event) throws Exception {
        super.onHalfOp(event);
    }

    @Override
    public void onIncomingChatRequest(IncomingChatRequestEvent event) throws Exception {
        super.onIncomingChatRequest(event);
    }

    @Override
    public void onIncomingFileTransfer(IncomingFileTransferEvent event) throws Exception {
        super.onIncomingFileTransfer(event);
    }

    @Override
    public void onInvite(InviteEvent event) throws Exception {
        super.onInvite(event);
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        super.onJoin(event);
        for (Sublistener sublistener : sublisteners){
            sublistener.onJoin(event);
        }
    }

    @Override
    public void onKick(KickEvent event) throws Exception {
        super.onKick(event);
    }

    @Override
    public void onListenerException(ListenerExceptionEvent event) throws Exception {
        super.onListenerException(event);
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        super.onMessage(event);
        for (Sublistener sublistener : sublisteners){
            sublistener.onMessageEvent(event);
        }
    }

    @Override
    public void onMode(ModeEvent event) throws Exception {
        super.onMode(event);
    }

    @Override
    public void onMotd(MotdEvent event) throws Exception {
        super.onMotd(event);
    }

    @Override
    public void onNickAlreadyInUse(NickAlreadyInUseEvent event) throws Exception {
        super.onNickAlreadyInUse(event);
    }

    @Override
    public void onNickChange(NickChangeEvent event) throws Exception {
        super.onNickChange(event);
    }

    @Override
    public void onNotice(NoticeEvent event) throws Exception {
        super.onNotice(event);
    }

    @Override
    public void onOp(OpEvent event) throws Exception {
        super.onOp(event);
    }

    @Override
    public void onOutput(OutputEvent event) throws Exception {
        super.onOutput(event);
    }

    @Override
    public void onOwner(OwnerEvent event) throws Exception {
        super.onOwner(event);
    }

    @Override
    public void onPart(PartEvent event) throws Exception {
        super.onPart(event);
    }

    @Override
    public void onPing(PingEvent event) throws Exception {
        super.onPing(event);
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        super.onPrivateMessage(event);
    }

    @Override
    public void onQuit(QuitEvent event) throws Exception {
        super.onQuit(event);
    }

    @Override
    public void onRemoveChannelBan(RemoveChannelBanEvent event) throws Exception {
        super.onRemoveChannelBan(event);
    }

    @Override
    public void onRemoveChannelKey(RemoveChannelKeyEvent event) throws Exception {
        super.onRemoveChannelKey(event);
    }

    @Override
    public void onRemoveChannelLimit(RemoveChannelLimitEvent event) throws Exception {
        super.onRemoveChannelLimit(event);
    }

    @Override
    public void onRemoveInviteOnly(RemoveInviteOnlyEvent event) throws Exception {
        super.onRemoveInviteOnly(event);
    }

    @Override
    public void onRemoveModerated(RemoveModeratedEvent event) throws Exception {
        super.onRemoveModerated(event);
    }

    @Override
    public void onRemoveNoExternalMessages(RemoveNoExternalMessagesEvent event) throws Exception {
        super.onRemoveNoExternalMessages(event);
    }

    @Override
    public void onRemovePrivate(RemovePrivateEvent event) throws Exception {
        super.onRemovePrivate(event);
    }

    @Override
    public void onRemoveSecret(RemoveSecretEvent event) throws Exception {
        super.onRemoveSecret(event);
    }

    @Override
    public void onRemoveTopicProtection(RemoveTopicProtectionEvent event) throws Exception {
        super.onRemoveTopicProtection(event);
    }

    @Override
    public void onServerPing(ServerPingEvent event) throws Exception {
        super.onServerPing(event);
    }

    @Override
    public void onServerResponse(ServerResponseEvent event) throws Exception {
        super.onServerResponse(event);
    }

    @Override
    public void onSetChannelBan(SetChannelBanEvent event) throws Exception {
        super.onSetChannelBan(event);
    }

    @Override
    public void onSetChannelKey(SetChannelKeyEvent event) throws Exception {
        super.onSetChannelKey(event);
    }

    @Override
    public void onSetChannelLimit(SetChannelLimitEvent event) throws Exception {
        super.onSetChannelLimit(event);
    }

    @Override
    public void onSetInviteOnly(SetInviteOnlyEvent event) throws Exception {
        super.onSetInviteOnly(event);
    }

    @Override
    public void onSetModerated(SetModeratedEvent event) throws Exception {
        super.onSetModerated(event);
    }

    @Override
    public void onSetNoExternalMessages(SetNoExternalMessagesEvent event) throws Exception {
        super.onSetNoExternalMessages(event);
    }

    @Override
    public void onSetPrivate(SetPrivateEvent event) throws Exception {
        super.onSetPrivate(event);
    }

    @Override
    public void onSetSecret(SetSecretEvent event) throws Exception {
        super.onSetSecret(event);
    }

    @Override
    public void onSetTopicProtection(SetTopicProtectionEvent event) throws Exception {
        super.onSetTopicProtection(event);
    }

    @Override
    public void onSocketConnect(SocketConnectEvent event) throws Exception {
        super.onSocketConnect(event);
    }

    @Override
    public void onSuperOp(SuperOpEvent event) throws Exception {
        super.onSuperOp(event);
    }

    @Override
    public void onTime(TimeEvent event) throws Exception {
        super.onTime(event);
    }

    @Override
    public void onTopic(TopicEvent event) throws Exception {
        super.onTopic(event);
    }

    @Override
    public void onUnknown(UnknownEvent event) throws Exception {
        super.onUnknown(event);
    }

    @Override
    public void onUserList(UserListEvent event) throws Exception {
        super.onUserList(event);
    }

    @Override
    public void onUserMode(UserModeEvent event) throws Exception {
        super.onUserMode(event);
    }

    @Override
    public void onVersion(VersionEvent event) throws Exception {
        super.onVersion(event);
    }

    @Override
    public void onVoice(VoiceEvent event) throws Exception {
        super.onVoice(event);
    }

    @Override
    public void onWhois(WhoisEvent event) throws Exception {
        super.onWhois(event);
    }

    @Override
    public void onGenericCTCP(GenericCTCPEvent event) throws Exception {
        super.onGenericCTCP(event);
    }

    @Override
    public void onGenericUserMode(GenericUserModeEvent event) throws Exception {
        super.onGenericUserMode(event);
    }

    @Override
    public void onGenericChannelMode(GenericChannelModeEvent event) throws Exception {
        super.onGenericChannelMode(event);
    }

    @Override
    public void onGenericChannelModeRecipient(GenericChannelModeRecipientEvent event) throws Exception {
        super.onGenericChannelModeRecipient(event);
    }

    @Override
    public void onGenericDCC(GenericDCCEvent event) throws Exception {
        super.onGenericDCC(event);
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event) throws Exception {
        super.onGenericMessage(event);
        for (Sublistener sublistener : sublisteners){
            sublistener.onGenericMessage(event);
        }
    }

    @Override
    public void onGenericChannel(GenericChannelEvent event) throws Exception {
        super.onGenericChannel(event);
    }

    @Override
    public void onGenericUser(GenericUserEvent event) throws Exception {
        super.onGenericUser(event);
    }

    @Override
    public void onGenericChannelUser(GenericChannelUserEvent event) throws Exception {
        super.onGenericChannelUser(event);
    }

    public void addSublistener(Sublistener sublistener){
        sublisteners.add(sublistener);
    }

    public interface Sublistener{
        void onMessageEvent(MessageEvent event);
        void onDisconnect(DisconnectEvent event);
        void onJoin(JoinEvent event);
        void onGenericMessage(GenericMessageEvent event);
        void onConnectAttemptFailed(ConnectAttemptFailedEvent event);
        void onConnect(ConnectEvent event);
    }
}


